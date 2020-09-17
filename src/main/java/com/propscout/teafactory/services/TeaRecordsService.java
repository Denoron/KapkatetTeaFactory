package com.propscout.teafactory.services;

import com.propscout.teafactory.models.MonthTeaWeight;
import com.propscout.teafactory.models.entities.Account;
import com.propscout.teafactory.models.entities.TeaRecord;
import com.propscout.teafactory.repositories.AccountRepository;
import com.propscout.teafactory.repositories.SettingsRepository;
import com.propscout.teafactory.repositories.TeaRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeaRecordsService {

    private final TeaRecordRepository teaRecordRepository;
    private final AccountRepository accountRepository;
    private final SettingsRepository settingsRepository;

    public TeaRecordsService(
            TeaRecordRepository teaRecordRepository,
            AccountRepository accountRepository,
            SettingsRepository settingsRepository
    ) {
        this.teaRecordRepository = teaRecordRepository;
        this.accountRepository = accountRepository;
        this.settingsRepository = settingsRepository;
    }


    public boolean addTeaRecord(TeaRecord teaRecord) {

        teaRecordRepository.save(teaRecord);

        return true;
    }

    public List<MonthTeaWeight> getThisMonthCumulativeTeaWeight() {

        return teaRecordRepository.getCumulativeAccountTeaRecords();
    }

    public void payFarmers() {

        Double pricePerKilo = settingsRepository.findById(1).isEmpty()
                ? 30.00
                : settingsRepository.findById(1).get().getPricePerKilo();

        teaRecordRepository.getCumulativeAccountTeaRecords()
                .forEach(monthTeaWeight -> {
                    Optional<Account> optionalAccount = accountRepository.findById(monthTeaWeight.getAccountId());

                    optionalAccount
                            .map(account -> {
                                account.setAccBal(monthTeaWeight.getTotalWeight() * pricePerKilo);
                                return account;
                            })
                            .ifPresent(accountRepository::save);
                });
    }


}
