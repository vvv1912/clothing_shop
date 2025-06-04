package com.shop.clothing.order.query.createInvoicePdf;

import com.lowagie.text.pdf.BaseFont;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.util.ClientUtil;
import com.shop.clothing.order.query.getOrderById.GetOrderByIdQuery;
import com.shop.clothing.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Service
public class CreateInvoicePdfQueryHandler implements IRequestHandler<CreateInvoicePdfQuery, String> {
    private final ISender _sender;
    private final ClientUtil _clientUtil;
    @Override
    public HandleResponse<String> handle(CreateInvoicePdfQuery createInvoicePdfQuery) throws Exception {
        // CHECK IF ALREADY EXIST FILE
        File file = new File(createInvoicePdfQuery.orderId() + ".pdf");
        if (file.exists()) {
            return HandleResponse.ok(createInvoicePdfQuery.orderId() + ".pdf");
        }
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        var order = _sender.send(new GetOrderByIdQuery(createInvoicePdfQuery.orderId()));


        Context context = new Context();
        context.setVariable("order", order.orThrow());
        context.setVariable("clientUtil", _clientUtil);
        String html = templateEngine.process("templates/invoice/index.html", context);
        html = convertToXhtml(html);
        OutputStream outputStream = new FileOutputStream(order.orThrow().getOrderId() + ".pdf");
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("static/css/Roboto-Regular.ttf",  BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        return HandleResponse.ok(order.orThrow().getOrderId() + ".pdf");
    }
    private String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(CharEncoding.UTF_8);
        tidy.setOutputEncoding(CharEncoding.UTF_8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(StandardCharsets.UTF_8);
    }
}
