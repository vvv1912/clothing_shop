package com.shop.clothing.report.query.getImportReport;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.report.dto.ImportProductReportDto;
import com.shop.clothing.report.dto.SoldReportDto;
import com.shop.clothing.report.query.getSoldReport.GetSoldReportQuery;
import com.shop.clothing.stockReceipt.entity.StockReceipt;
import com.shop.clothing.stockReceipt.repository.StockReceiptRepository;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class GetImportReportQueryHandler implements IRequestHandler<GetImportReportQuery, List<ImportProductReportDto>> {
    private final StockReceiptRepository stockReceiptRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "importReport", key = "#query.toString()")
    public HandleResponse<List<ImportProductReportDto>> handle(GetImportReportQuery query) throws Exception {
        var startDate = query.getStartDate();
        var endDate = query.getEndDate();
        List<Tuple> reportList = stockReceiptRepository.getImportReport(startDate, endDate);
        var importReportDtoList = reportList.stream().map(tuple -> {
            var reportDto = new ImportProductReportDto();
            reportDto.setDate(tuple.get("date", Date.class));
            reportDto.setTotalImport(Math.toIntExact(tuple.get("totalOrder", Long.class)));
            reportDto.setTotalCost(Math.toIntExact(tuple.get("totalRevenue", Long.class)));
            reportDto.setTotalQuantityImport(Math.toIntExact(tuple.get("totalQuantityImport", Long.class)));
            return reportDto;
        }).toList();
        return HandleResponse.ok(importReportDtoList);
    }
}
