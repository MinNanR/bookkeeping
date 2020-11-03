package site.minnan.bookkeeping.domain.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.service.JournalService;
import site.minnan.bookkeeping.domain.service.JournalTypeService;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.domain.vo.JournalListItemVO;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    private JournalTypeService journalTypeService;

    @Autowired
    private WarehouseService warehouseService;

    /**
     * 装配流水记录
     *
     * @param journalList
     * @return
     */
    @Override
    public List<JournalListItemVO> assembleJournal(List<Journal> journalList) {
        Set<Integer> journalTypeIds = new HashSet<>();
        Set<Integer> warehouseIds = new HashSet<>();
        Set<Integer> currencyIds = new HashSet<>();
        for (Journal journal : journalList) {
            journalTypeIds.add(journal.getJournalTypeId());
            warehouseIds.add(journal.getWarehouseId());
            currencyIds.add(journal.getCurrencyId());
        }
        Map<Integer, String> typeMap = journalTypeService.mapIdToName(journalTypeIds);
        Map<Integer, String> warehouseMap = warehouseService.mapIdToWarehouseName(warehouseIds);
        Map<Integer, String> currencyMap = warehouseService.mapIdToCurrencyName(currencyIds);
        return journalList.stream()
                .map(j -> JournalListItemVO.assemble(j.getId(), j.getAmount(), warehouseMap.get(j.getWarehouseId()),
                        typeMap.get(j.getJournalTypeId()), currencyMap.get(j.getCurrencyId()),
                        j.getJournalDirection(), j.getJournalTime(), j.getRemark()))
                .collect(Collectors.toList());
    }
}
