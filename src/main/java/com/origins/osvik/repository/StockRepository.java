package com.origins.osvik.repository;

import com.origins.osvik.domain.Stock;
import com.origins.osvik.dto.StockRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Query(value = "SELECT item FROM Stock item WHERE item.code=:code")
    Stock findOneByCode(@Param("code") String code);

    @Query(value = "SELECT new com.origins.osvik.dto.StockRepresentation(stock.id,item.code,item.name,SUM (stock.qty),stock.doe,stock.lotNo,stock.batchNo,item.description,stock.price,item.supplier.name) FROM Stock stock , Item item WHERE stock.code=item.code GROUP BY stock.code,stock.price")
    Page<StockRepresentation> findAllByPage(Pageable pageable);

    @Query(value = "SELECT new com.origins.osvik.dto.StockRepresentation(stock.id,item.code,item.name,SUM (stock.qty),stock.doe,stock.lotNo,stock.batchNo,item.description,stock.price,item.supplier.name) FROM Stock stock , Item item WHERE stock.code=item.code GROUP BY stock.code,stock.price")
    List<StockRepresentation> findAllStock();

    @Query(value = "SELECT SUM (stock.qty) FROM Stock stock , Item item WHERE stock.code=item.code AND item.code=:code GROUP BY stock.code")
    Double getStockByItem(@Param("code") String code);

    @Query(value = "SELECT new com.origins.osvik.dto.StockRepresentation(stock.id,item.code,item.name,SUM (stock.qty),stock.doe,stock.lotNo,stock.batchNo,item.description,stock.price,item.supplier.name) FROM Stock stock , Item item WHERE stock.code=item.code AND ((stock.lotNo LIKE :query OR stock.batchNo LIKE :query OR item.name LIKE :query OR item.code LIKE :query OR item.description LIKE :query) AND item.category.name LIKE :mCat AND item.subCategory.name LIKE :sCat AND item.supplier.name LIKE :supplier) GROUP BY stock.code,stock.price")
    Page<StockRepresentation> getSearchAllByPage(@Param("query") String query, @Param("mCat") String mCat, @Param("sCat") String sCat, @Param("supplier") String supplier, Pageable pageable);

    @Query(value = "SELECT item FROM Stock item WHERE item.code=:code AND item.invoiceNo=:invoiceNo AND item.price =:unitPrice")
    Stock findOneByInvoiceNoAndItemCode(@Param("invoiceNo") String invoiceNo, @Param("code") String code, @Param("unitPrice") Double unitPrice);
}
