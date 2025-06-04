package com.shop.clothing.report.query.getTopSoldProductReport;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.dto.ProductBriefDto;
import com.shop.clothing.product.repository.ProductRepository;
import com.shop.clothing.report.dto.ProductReportDto;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.List;

@AllArgsConstructor
@Service
public class GetTopSoldProductReportQueryHandler implements IRequestHandler<GetTopSoldProductReportQuery, List<ProductReportDto>> {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "topSoldProductReport", key = "#getTopSoldProductReportQuery.toString()")
    public HandleResponse<List<ProductReportDto>> handle(GetTopSoldProductReportQuery getTopSoldProductReportQuery) throws Exception {
        List<Tuple> productReportDtoTupleList = productRepository.getSoldReport(getTopSoldProductReportQuery.getStartDate(), getTopSoldProductReportQuery.getEndDate());
        List<ProductReportDto> productReportDtoList = productReportDtoTupleList.stream().map(tuple -> {
            var productReportDto = new ProductReportDto();
            var productBriefDto = new ProductBriefDto();
            int productID = tuple.get("productId", Integer.class);
            productReportDto.setTotalSold(Math.toIntExact(tuple.get("total_sold", Long.class)));
            productReportDto.setTotalRevenue(Math.toIntExact(tuple.get("total_revenue", Long.class)));
            productBriefDto.setProductId(productID);
            productReportDto.setProduct(productBriefDto);
            return productReportDto;
        }).toList();
        var productBriefDtoList = productRepository.findByProductIdIn(productReportDtoList.stream().map((p) -> p.getProduct().getProductId()).toList());
        productReportDtoList.forEach((p) -> {
            var productBriefDto = productBriefDtoList.stream().filter((pb) -> pb.getProductId() == p.getProduct().getProductId()).findFirst().get();
            p.setProduct(modelMapper.map(productBriefDto, ProductBriefDto.class));
        });
        return HandleResponse.ok(productReportDtoList);
    }
}
