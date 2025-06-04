package com.shop.clothing.stockReceipt.query.getAllSuppliers;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.PaginationRequest;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.stockReceipt.dto.StockReceiptBriefDto;
import com.shop.clothing.stockReceipt.dto.SupplierDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class GetAllSuppliersQuery extends PaginationRequest implements IRequest<Paginated<SupplierDto>> {
}
