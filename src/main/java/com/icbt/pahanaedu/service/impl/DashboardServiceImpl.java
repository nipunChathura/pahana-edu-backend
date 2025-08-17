package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.dto.DashboardDto;
import com.icbt.pahanaedu.entity.Book;
import com.icbt.pahanaedu.entity.MembershipType;
import com.icbt.pahanaedu.repository.CustomerRepository;
import com.icbt.pahanaedu.repository.OrderDetailRepository;
import com.icbt.pahanaedu.repository.OrderRepository;
import com.icbt.pahanaedu.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public DashboardDto getDashboardData(DashboardDto dashboardDto) {
        log.info(LogSupport.DASHBOARD_LOG + "stating.", "getDashboardData()", dashboardDto.getUserId());

        getCustomerCount(dashboardDto);

        getOrderCount(dashboardDto);

        getOrderIncomes(dashboardDto);

        getPopularBooks(dashboardDto);

        log.info(LogSupport.DASHBOARD_LOG + "ending.", "getDashboardData()", dashboardDto.getUserId());
        return dashboardDto;
    }

    private void getCustomerCount(DashboardDto dashboardDto) {
        log.info(LogSupport.DASHBOARD_LOG + "setup customer count by membership.", "getDashboardData()", dashboardDto.getUserId());
        List<Object[]> objects = customerRepository.countCustomersGroupedByMembershipType();
        HashMap<String, Long> membershipCount = new HashMap<>();
        objects.forEach(object -> {
            membershipCount.put(object[0].toString(), (Long) object[1]);
        });
        Set<String> types = membershipCount.keySet();
        if (!types.contains(MembershipType.GOLD.name())) {
            membershipCount.put(MembershipType.GOLD.name(), 0L);
        }
        if (!types.contains(MembershipType.DIAMOND.name())) {
            membershipCount.put(MembershipType.DIAMOND.name(), 0L);
        }
        if (!types.contains(MembershipType.PLATINUM.name())) {
            membershipCount.put(MembershipType.PLATINUM.name(), 0L);
        }
        if (!types.contains(MembershipType.SILVER.name())) {
            membershipCount.put(MembershipType.SILVER.name(), 0L);
        }
        dashboardDto.setMembershipCount(membershipCount);
        log.info(LogSupport.DASHBOARD_LOG + "setup end customer count by membership.", "getDashboardData()", dashboardDto.getUserId());
    }

    private void getOrderCount(DashboardDto dashboardDto) {
        log.info(LogSupport.DASHBOARD_LOG + "setup order count by daily.", "getDashboardData()", dashboardDto.getUserId());
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Object[]> results = orderRepository.findDailyOrderCountSince(sevenDaysAgo);
        LinkedHashMap<String, Long> orderCount = new LinkedHashMap<>();
        Map<LocalDate, Long> tempMap1 = new HashMap<>();
        for (Object[] row : results) {
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            long count = ((Number) row[1]).longValue(); // safe casting
            tempMap1.put(date, count);
        }

        LocalDate today1 = LocalDate.now();
        String todayName1 = today1.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        for (int i = 0; i < 7; i++) {
            LocalDate date = sevenDaysAgo.toLocalDate().plusDays(i);
            String dayName = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            if (!dayName.equals(todayName1)) {
                long count = tempMap1.getOrDefault(date, 0L);
                orderCount.put(dayName, count);
            }
        }
        long todayCount1 = tempMap1.getOrDefault(today1, 0L);
        orderCount.put(todayName1, todayCount1);

        dashboardDto.setOrderCount(orderCount);
        log.info(LogSupport.DASHBOARD_LOG + "setup end order count by daily.", "getDashboardData()", dashboardDto.getUserId());
    }
    private void getOrderIncomes(DashboardDto dashboardDto) {
        log.info(LogSupport.DASHBOARD_LOG + "setup order income by daily.", "getDashboardData()", dashboardDto.getUserId());
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Object[]> incomeResults = orderRepository.findDailyIncomeSince(sevenDaysAgo);
        LinkedHashMap<String, Double> income = new LinkedHashMap<>();

        Map<LocalDate, Double> tempMap = new HashMap<>();
        for (Object[] row : incomeResults) {
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            BigDecimal count = (BigDecimal) row[1];
            tempMap.put(date, count.doubleValue());
        }

        LocalDate today = LocalDate.now();
        String todayName = today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        List<String> dayOrder = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.from(sevenDaysAgo.plusDays(i));
            String dayName = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            if (!dayName.equals(todayName)) {
                Double count = tempMap.getOrDefault(date, 0.0);
                income.put(dayName, count);
                dayOrder.add(dayName);
            }
        }

        Double todayCount = tempMap.getOrDefault(today, 0.0);
        income.put(todayName, todayCount);
        dayOrder.add(todayName);

        dashboardDto.setIncomes(income);
        log.info(LogSupport.DASHBOARD_LOG + "setup end order income by daily.", "getDashboardData()", dashboardDto.getUserId());
    }
    private void getPopularBooks(DashboardDto dashboardDto) {
        log.info(LogSupport.DASHBOARD_LOG + "setup popular 5 books stock availability.", "getDashboardData()", dashboardDto.getUserId());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyDaysAgo = now.minusDays(30).withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Book> topSellingBooks = orderDetailRepository.findTopSellingBooks(thirtyDaysAgo, now, PageRequest.of(0, 5));
        HashMap<String, Double> popularBookStock = new HashMap<>();
        if (!topSellingBooks.isEmpty()) {
            topSellingBooks.forEach(book -> {
                double availability = ((book.getQuantity().doubleValue() / book.getLoadingQuantity().doubleValue()) * 100);
                popularBookStock.put(book.getName(), availability);
            });
        }
        dashboardDto.setPopularBookStock(popularBookStock);
        log.info(LogSupport.DASHBOARD_LOG + "setup end popular 5 books stock availability.", "getDashboardData()", dashboardDto.getUserId());
    }
}
