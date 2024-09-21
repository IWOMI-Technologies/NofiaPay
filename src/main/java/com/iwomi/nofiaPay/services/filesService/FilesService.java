package com.iwomi.nofiaPay.services.filesService;

import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilesService implements IFilesService {
    private final ClientRepository clientRepository;

    private final AccountRepository accountRepository;

    private final AccountHistoryRepository accountHistoryRepository;

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

                if (isRowEmpty(row)) {
                    continue;  //Skip empty rows
                }

                System.out.println("cell data****** 0 " + getValue(row, 0));
                System.out.println("cell data****** 1 " + getValue(row, 1));
                System.out.println("cell data****** 2 " + getValue(row, 2));
                System.out.println("cell data****** 3 " + getValue(row, 3));
                System.out.println("cell data****** 4 " + getValue(row, 4));
                System.out.println("cell data****** 5 " + getValue(row, 5));
                System.out.println("cell data****** 6 " + getValue(row, 6));
                System.out.println("cell data****** 7 " + getValue(row, 7));
                System.out.println("cell data****** 8 " + getValue(row, 8));
                System.out.println("cell data****** 9 " + getValue(row, 9));
                System.out.println("cell data****** 10 " + getValue(row, 10));
                System.out.println("cell data****** 11 " + getValue(row, 11));
                System.out.println("cell data****** 12 " + getValue(row, 12));
                System.out.println("cell data****** 13 " + getValue(row, 13));
                System.out.println("cell data****** 14 " + getValue(row, 14));
                System.out.println("cell data****** 15 " + getValue(row, 15));
                System.out.println("cell data****** 16 " + getValue(row, 16));
                System.out.println("cell data****** 17 " + getValue(row, 17));
                System.out.println("cell data****** 18 " + getValue(row, 18));
                System.out.println("cell data****** 19 " + getValue(row, 19));
                System.out.println("cell data****** 20 " + getValue(row, 20));
                System.out.println("cell data****** 21 " + getValue(row, 21));
                System.out.println("cell data****** 22 " + getValue(row, 22));
                System.out.println("cell data****** 23 " + getValue(row, 23));
                System.out.println("cell data****** 24 " + getValue(row, 24));


                ClientEntity client = new ClientEntity();
                client.setClientCode(getValue(row, 0).toString());
                client.setFirstName(getValue(row, 1).toString());
                client.setLastName(getValue(row, 2).toString());
                client.setFullName(getValue(row, 3).toString());
                client.setAgencyCode(getValue(row, 4).toString());
                client.setAgencyLabel(getValue(row, 5).toString());
                client.setManagerCode(getValue(row, 6).toString());
                client.setManagerName(getValue(row, 7).toString());
                client.setFirstAddress(getValue(row, 8).toString());
                client.setSecondAddress(getValue(row, 9).toString());
                client.setClientType(getValue(row, 10).toString());
                client.setDateOfBirth(DateConverterUtils.convertToDate(getValue(row, 11).toString()));
                client.setPlaceOfBirth(getValue(row, 12).toString());
                client.setIdNumber(getValue(row, 13).toString());
                client.setIdDeliveryDate(DateConverterUtils.convertToDate(getValue(row, 14).toString()));
                client.setIdDeliveryPlace(getValue(row, 15).toString());
                client.setIdExpirationDate(DateConverterUtils.convertToDate(getValue(row, 16).toString()));
                client.setCommercialRegNum(getValue(row, 17).toString());
                client.setTaxPayerNumber(getValue(row, 18).toString());
                client.setBusinessCreationDate(DateConverterUtils.convertToDate(getValue(row, 19).toString()));
                client.setNotificationPhoneNumber(getValue(row, 20).toString());
                client.setPhoneNumber(getValue(row, 21).toString());
                client.setClientCreationDate(DateConverterUtils.convertToDate(getValue(row, 22).toString()));
                client.setEmail(getValue(row, 23).toString());
                client.setAgentCode(getValue(row, 24).toString());
                client.setAgentName(getValue(row, 25).toString());
                //         client.setIdDeliveryAuthority(row.getCell(18).getStringCellValue());

//                clientRepository.save(client);
                clientsList.add(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("output ------- " + clientsList);
        List<ClientEntity> result = clientRepository.createManyClients(clientsList);
        System.out.println("output ------- " + clientsList);

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

                if (isRowEmpty(row)) {
                    continue;  //Skip empty rows
                }



                System.out.println("cell data****** " + row.getCell(0));
                System.out.println("cell data****** " + row.getCell(1));
                System.out.println("cell data****** " + row.getCell(2));

                System.out.println("cell data****** 5" + row.getCell(5));
                System.out.println("cell data****** 10" + row.getCell(10));
                System.out.println("cell data****** 14 " + row.getCell(14));

                AccountEntity account = new AccountEntity();
                account.setAgencyCode(row.getCell(0).getStringCellValue());
                account.setAgencyName(row.getCell(1).getStringCellValue());
                account.setCurrency(row.getCell(2).getStringCellValue());
                account.setCle(row.getCell(3).getStringCellValue());
                account.setAccountTitle(row.getCell(4).getStringCellValue());
                account.setChapter(String.valueOf(row.getCell(5).getNumericCellValue()));

                account.setAccountTypeCode(String.valueOf(row.getCell(5).getNumericCellValue()));
                account.setChapterTitle(row.getCell(6).getStringCellValue());

                account.setAccountTypeCode(String.valueOf(row.getCell(7).getNumericCellValue()));
//                account.setAccountTypeCode(row.getCell(7).getStringCellValue());
                account.setAccountTypeLabel(row.getCell(8).getStringCellValue());
                account.setAccountNumber(row.getCell(9).getStringCellValue());
//                account.setStartDate(row.getCell(10).getDateCellValue());
                String dateStr = row.getCell(10).getStringCellValue();
                account.setStartDate(DateConverterUtils.convertToDate(dateStr));

//                account.setEndDate(row.getCell(11).getDateCellValue());
                account.setEndDate(DateConverterUtils.convertToDate(row.getCell(11).getStringCellValue()));
                account.setDebit(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
                account.setCredit(BigDecimal.valueOf(row.getCell(13).getNumericCellValue()));
                account.setClientCode(row.getCell(14).getStringCellValue());

                accountsList.add(account);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("output ------- " + accountsList);
        List<AccountEntity> result = accountRepository.saveAllAccounts(accountsList);
        System.out.println("output ------- " + accountsList);

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

                if (isRowEmpty(row)) {
                    continue;  //Skip empty rows
                }

                System.out.println("cell data****** " + row.getCell(0));
                System.out.println("cell data****** " + row.getCell(1));
                System.out.println("cell data****** " + row.getCell(2));

                System.out.println("cell data****** 9" + row.getCell(9));
                System.out.println("cell data****** 10" + row.getCell(10));
                System.out.println("cell data****** 12 " + row.getCell(12));

                AccountHistoryEntity accountHistory = new AccountHistoryEntity();
                accountHistory.setAgencyCode(row.getCell(0).getStringCellValue());
                accountHistory.setAccountNumber(row.getCell(1).getStringCellValue());
                accountHistory.setCurrency(row.getCell(2).getStringCellValue());
                accountHistory.setCle(row.getCell(3).getStringCellValue());
                accountHistory.setOperationCode(row.getCell(4).getStringCellValue());
                accountHistory.setOperationTitle(row.getCell(5).getStringCellValue());
                accountHistory.setTransactionReference(row.getCell(6).getStringCellValue());
                accountHistory.setAmount(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
                accountHistory.setSense(SenseTypeEnum.valueOf(row.getCell(8).getStringCellValue().toUpperCase()));
                accountHistory.setAccountingDocument(row.getCell(9).getStringCellValue());
                accountHistory.setAccountingDate(DateConverterUtils.convertToDate(getValue(row, 10).toString()));
                accountHistory.setValueDate(getValue(row, 11).toString());
                accountHistory.setBalance(getValue(row, 12).toString());


//                clientRepository.save(client);
                accountHisoryList.add(accountHistory);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("output ------- " + accountHisoryList);
        List<AccountHistoryEntity> result = accountHistoryRepository.saveAllHistories(accountHisoryList);
        System.out.println("output ------- " + accountHisoryList);

        return !result.isEmpty();
    }

    private boolean isRowEmpty(Row row) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private Object getValue(Row row, int value) {
        if (row.getCell(value).getCellType() == CellType.NUMERIC) {

            if (DateUtil.isCellDateFormatted(row.getCell(value))) {
                // If the cell contains a date, format it as needed
                Date dateValue = row.getCell(value).getDateCellValue();
                return new SimpleDateFormat("yyyy-MM-dd").format(dateValue);
            }

            return String.valueOf((int) row.getCell(value).getNumericCellValue());
        } else if (row.getCell(value).getCellType() == CellType.STRING) {
            return row.getCell(value).getStringCellValue();
        } else {
            // Handle other possible cell types (optional)
            System.out.println("Unsupported cell type at row: " + row.getRowNum()+" : "+ row.getCell(value));
        }
        throw new GeneralException("something went wrong");
    }
}
