package com.closing.inventory.service.analyze;

import com.closing.inventory.model.expenditure.ExpenditureCapex;
import com.closing.inventory.model.expenditure.ExpenditureOpex;
import com.closing.inventory.model.sale.Sale;
import com.closing.inventory.repository.expenditure.ExpenditureCapexRepository;
import com.closing.inventory.repository.expenditure.ExpenditureOpexRepository;
import com.closing.inventory.repository.sale.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class AnalyzeService {

    @Autowired
    private ExpenditureCapexRepository expenditureCapexRepository;

    @Autowired
    private ExpenditureOpexRepository expenditureOpexRepository;

    @Autowired
    private SaleRepository saleRepository;

    public BigDecimal[] listSaleMonths() {
        int year = 2024;
        BigDecimal[] listSaleMonths = new BigDecimal[12];
        for (int month = 1; month <= 12; month++) {
            LocalDateTime from = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime to = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

            BigDecimal totalValue = BigDecimal.ZERO;
            List<Sale> listSale = saleRepository.findAllByLocalDateTimeBetween(from, to);
            for (Sale sale : listSale) {
                totalValue = totalValue.add(sale.getValue());
            }
            listSaleMonths[month - 1] = totalValue;
        }
        return listSaleMonths;
    }

    public BigDecimal[] listCapexMonths() {
        int year = 2024;
        BigDecimal[] listCapexMonths = new BigDecimal[12];
        for (int month = 1; month <= 12; month++) {
            LocalDateTime from = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime to = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

            BigDecimal totalValue = BigDecimal.ZERO;
            List<ExpenditureCapex> listCapex = expenditureCapexRepository.findAllByLocalDateTimeBetween(from, to);
            for (ExpenditureCapex expenditure : listCapex) {
                totalValue = totalValue.add(expenditure.getValue());
            }
            listCapexMonths[month - 1] = totalValue;
        }
        return listCapexMonths;
    }

    public BigDecimal[] listOpexMonths() {
        int year = 2024;
        BigDecimal[] listOpexMonths = new BigDecimal[12];
        for (int month = 1; month <= 12; month++) {
            LocalDateTime from = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime to = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

            BigDecimal totalValue = BigDecimal.ZERO;
            List<ExpenditureOpex> listOpex = expenditureOpexRepository.findAllByLocalDateTimeBetween(from, to);
            for (ExpenditureOpex expenditure : listOpex) {
                totalValue = totalValue.add(expenditure.getValue());
            }
            listOpexMonths[month - 1] = totalValue;
        }
        return listOpexMonths;
    }

    public BigDecimal[] listExpendituresMonths() {
        int year = 2024;
        BigDecimal[] listExpendituresMonths = new BigDecimal[12];

        for (int month = 1; month <= 12; month++) {
            LocalDateTime from = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime to = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

            BigDecimal totalValue = BigDecimal.ZERO;
            List<ExpenditureOpex> listOpex = expenditureOpexRepository.findAllByLocalDateTimeBetween(from, to);
            for (ExpenditureOpex expenditureOpex : listOpex) {
                totalValue = totalValue.add(expenditureOpex.getValue());
            }
            List<ExpenditureCapex> listCapex = expenditureCapexRepository.findAllByLocalDateTimeBetween(from, to);
            for (ExpenditureCapex expenditureCapex : listCapex) {
                totalValue = totalValue.add(expenditureCapex.getValue());
            }

            listExpendituresMonths[month - 1] = totalValue;
        }
        return listExpendituresMonths;
    }

    public BigDecimal[] listProfitMonths() {
        int year = 2024;
        BigDecimal[] listProfitMonths = new BigDecimal[12];

        for (int month = 1; month <= 12; month++) {
            LocalDateTime from = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime to = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

            BigDecimal valueTotalExpenditure = BigDecimal.ZERO;
            List<ExpenditureOpex> listOpex = expenditureOpexRepository.findAllByLocalDateTimeBetween(from, to);
            for (ExpenditureOpex opex : listOpex) {
                valueTotalExpenditure = valueTotalExpenditure.add(opex.getValue());
            }
            List<ExpenditureCapex> listCapex = expenditureCapexRepository.findAllByLocalDateTimeBetween(from, to);
            for (ExpenditureCapex capex : listCapex) {
                valueTotalExpenditure = valueTotalExpenditure.add(capex.getValue());
            }

            BigDecimal valueTotalSale = BigDecimal.ZERO;
            List<Sale> listSale = saleRepository.findAllByLocalDateTimeBetween(from, to);
            for (Sale sale : listSale) {
                valueTotalSale = valueTotalSale.add(sale.getValue());
            }

            BigDecimal profit = valueTotalSale.subtract(valueTotalExpenditure);
            listProfitMonths[month - 1] = profit;
        }
        return listProfitMonths;
    }
}
