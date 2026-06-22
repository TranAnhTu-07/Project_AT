package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.projectweb.dao.SignatureOrderDao;
import vn.edu.nlu.fit.projectweb.dao.UserKeyDao;
import vn.edu.nlu.fit.projectweb.model.UserKey;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

@WebServlet(name = "VerifySignatureServlet", value = "/VerifySignature")
public class VerifySignatureServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không cho phép truy cập trực tiếp bằng GET, chuyển về trang đặt hàng/tra cứu
        response.sendRedirect(request.getContextPath() + "/html/Order.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int orderId;
        try {
            orderId = Integer.parseInt(request.getParameter("orderId"));
        } catch (Exception e) {
            request.setAttribute("error", "orderId không hợp lệ.");
            request.getRequestDispatcher("/html/VerifySignature.jsp").forward(request, response);
            return;
        }

        String signatureBase64 = request.getParameter("signature");

        Optional<SignatureOrderDao.OrderSignInfo> infoOpt = SignatureOrderDao.buildDataToSign(orderId);

        if (infoOpt.isEmpty()) {
            request.setAttribute("error", "Không tìm thấy đơn hàng #" + orderId);
            request.getRequestDispatcher("/html/VerifySignature.jsp").forward(request, response);
            return;
        }

        SignatureOrderDao.OrderSignInfo info = infoOpt.get();

        if (info.userId == null) {
            request.setAttribute("error", "Đơn hàng này không gắn với tài khoản nào, không thể xác thực chữ ký.");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/html/VerifySignature.jsp").forward(request, response);
            return;
        }

        if (signatureBase64 == null || signatureBase64.isBlank()) {
            request.setAttribute("error", "Vui lòng dán chữ ký.");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/html/VerifySignature.jsp").forward(request, response);
            return;
        }

        Optional<UserKey> keyOpt = UserKeyDao.getLatestActiveKey(info.userId);

        if (keyOpt.isEmpty()) {
            request.setAttribute("error", "Không tìm thấy public key đang hoạt động cho tài khoản này.");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/html/VerifySignature.jsp").forward(request, response);
            return;
        }

        boolean isValid;
        try {
            isValid = verifySignature(info.dataToSign, signatureBase64, keyOpt.get().getPublicKey());
        } catch (Exception e) {
            getServletContext().log("Lỗi xác thực chữ ký đơn hàng #" + orderId, e);
            request.setAttribute("error", "Không thể xác thực chữ ký. Vui lòng kiểm tra lại dữ liệu.");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/html/VerifySignature.jsp").forward(request, response);
            return;
        }

        SignatureOrderDao.saveSignatureResult(orderId, signatureBase64, isValid);

        request.setAttribute("isValid", isValid);
        request.setAttribute("orderId", orderId);
        request.setAttribute("customerName", info.customerName);
        request.getRequestDispatcher("/html/VerifySignature.jsp").forward(request, response);
    }

    private boolean verifySignature(String data, String signatureBase64, String publicKeyBase64) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);

        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data.getBytes("UTF-8"));

        byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
        return sig.verify(signatureBytes);
    }
}