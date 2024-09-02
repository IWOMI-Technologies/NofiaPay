package com.iwomi.nofiaPay.services.filesService;

import com.iwomi.nofiaPay.core.utils.DateConverterUtils;
import com.iwomi.nofiaPay.dtos.UploadDto;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory.AccountHistoryRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilesService implements IFilesService{
    private final ClientRepository clientRepository;

    private final AccountRepository accountRepository;

    private  final AccountHistoryRepository accountHistoryRepository;

    @Override
    public Boolean importFile(UploadDto dto) {

        return switch (dto.type()) {
            case CLIENT_FILE -> clientUpload(dto.file());
            case ACCOUNT_FILE -> accountUpload(dto.file());
            case ACCOUNT_HISTORY_FILE -> accountHistoryUpload(dto.file());
        };

    }

    private Boolean clientUpload(MultipartFile file) {
        List<ClientEntity> clientsList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);


            for (Row row : sheet) {
                if (row.getRowNum() == 0) {  // Skip header row
                    continue;
                }

                System.out.println("cell data****** "+row.getCell(0));
                System.out.println("cell data****** "+row.getCell(1));
                System.out.println("cell data****** "+row.getCell(2));

                System.out.println("cell data****** 9"+row.getCell(9));
                System.out.println("cell data****** 10"+row.getCell(10));
                System.out.println("cell data****** 22 "+row.getCell(22));

                ClientEntity client = new ClientEntity();
                client.setClientCode(row.getCell(0).getStringCellValue());
                client.setFirstName(row.getCell(1).getStringCellValue());
                client.setLastName(row.getCell(2).getStringCellValue());
                client.setFullName(row.getCell(3).getStringCellValue());
                client.setAgencyCode(row.getCell(4).getStringCellValue());
                client.setAgencyLabel(row.getCell(5).getStringCellValue());
                client.setManagerCode(row.getCell(6).getStringCellValue());
                client.setManagerName(row.getCell(7).getStringCellValue());
                client.setFirstAddress(row.getCell(8).getStringCellValue());
                client.setSecondAddress(row.getCell(9).getStringCellValue());
                client.setClientType(row.getCell(10).getStringCellValue());
                client.setAgentCode(row.getCell(11).getStringCellValue());
                client.setAgentName(row.getCell(12).getStringCellValue());
                client.setPhoneNumber(row.getCell(13).getStringCellValue());
                client.setNotificationPhoneNumber(row.getCell(14).getStringCellValue());
                client.setEmail(row.getCell(15).getStringCellValue());
                client.setIdNumber(row.getCell(16).getStringCellValue());
                client.setIdDeliveryPlace(row.getCell(17).getStringCellValue());
                client.setIdDeliveryAuthority(row.getCell(18).getStringCellValue());
                client.setPlaceOfBirth(row.getCell(19).getStringCellValue());
                client.setCommercialRegNum(row.getCell(20).getStringCellValue());
                client.setTaxPayerNumber(row.getCell(21).getStringCellValue());
                client.setBusinessCreationDate(row.getCell(22).getDateCellValue());
                client.setClientCreationDate(row.getCell(23).getDateCellValue());
                client.setIdDeliveryDate(row.getCell(24).getDateCellValue());
                client.setIdExpirationDate(row.getCell(25).getDateCellValue());
                client.setDateOfBirth(row.getCell(26).getDateCellValue());

//                clientRepository.save(client);
                clientsList.add(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("output ------- "+clientsList);
        List<ClientEntity> result = clientRepository.createManyClients(clientsList);
        System.out.println("output ------- "+clientsList);

        return !result.isEmpty();
    }

    private Boolean accountUpload(MultipartFile file) {
        List<AccountEntity> accountsList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {  // Skip header row
                    continue;
                }

                System.out.println("cell data****** "+row.getCell(0));
                System.out.println("cell data****** "+row.getCell(1));
                System.out.println("cell data****** "+row.getCell(2));

                System.out.println("cell data****** 9"+row.getCell(9));
                System.out.println("cell data****** 10"+row.getCell(10));
                System.out.println("cell data****** 14 "+row.getCell(14));

                AccountEntity account = new AccountEntity();
                account.setClientCode(row.getCell(0).getStringCellValue());
                account.setAccountNumber(row.getCell(1).getStringCellValue());
                account.setAccountTitle(row.getCell(2).getStringCellValue());
                account.setAgencyName(row.getCell(3).getStringCellValue());
                account.setAgencyCode(row.getCell(4).getStringCellValue());
                account.setBalance(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
                account.setAccountTypeLabel(row.getCell(6).getStringCellValue());
                account.setChapter(row.getCell(7).getStringCellValue());
                account.setCurrency(row.getCell(8).getStringCellValue());
                account.setCle(row.getCell(9).getStringCellValue());
                account.setChapterTitle(row.getCell(10).getStringCellValue());
                account.setClientCode(row.getCell(11).getStringCellValue());
                account.setCreatedAt(row.getCell(12).getDateCellValue());
                account.setAccountCreation(row.getCell(13).getDateCellValue());
                account.setUpdatedAt(row.getCell(14).getDateCellValue());

//                clientRepository.save(client);
                accountsList.add(account);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("output ------- "+accountsList);
        List<AccountEntity> result = accountRepository.saveAllAccounts(accountsList);
        System.out.println("output ------- "+accountsList);

        return !result.isEmpty();

    }

    private Boolean accountHistoryUpload(MultipartFile file) {
        List<AccountHistoryEntity> accountHisoryList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {  // Skip header row
                    continue;
                }

                System.out.println("cell data****** "+row.getCell(0));
                System.out.println("cell data****** "+row.getCell(1));
                System.out.println("cell data****** "+row.getCell(2));

                System.out.println("cell data****** 9"+row.getCell(9));
                System.out.println("cell data****** 10"+row.getCell(10));
                System.out.println("cell data****** 14 "+row.getCell(14));

                AccountHistoryEntity accountHistory = new AccountHistoryEntity();
                accountHistory.setAccountingDocument(row.getCell(0).getStringCellValue());
                accountHistory.setAccountNumber(row.getCell(1).getStringCellValue());
                accountHistory.setAccountingDate(row.getCell(2).getStringCellValue());
                accountHistory.setCle(row.getCell(3).getStringCellValue());
                accountHistory.setAmount(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
                accountHistory.setAgencyCode(row.getCell(5).getStringCellValue());
                accountHistory.setBalance(row.getCell(6).getStringCellValue());
                accountHistory.setOperationTitle(row.getCell(7).getStringCellValue());
                accountHistory.setOperationCode(row.getCell(8).getStringCellValue());
                accountHistory.setTransactionReference(row.getCell(9).getStringCellValue());
                accountHistory.setCurrency(row.getCell(10).getStringCellValue());
                accountHistory.setCle(row.getCell(11).getStringCellValue());
                accountHistory.setCreatedAt(row.getCell(12).getDateCellValue());
                accountHistory.setValueDate(row.getCell(13).getStringCellValue());
                accountHistory.setUpdatedAt(row.getCell(14).getDateCellValue());


//                clientRepository.save(client);
                accountHisoryList.add(accountHistory);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("output ------- "+accountHisoryList);
        List<AccountHistoryEntity> result = accountHistoryRepository.saveAllHistories(accountHisoryList);
        System.out.println("output ------- "+accountHisoryList);

        return !result.isEmpty();
    }
}
