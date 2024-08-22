package com.iwomi.nofiaPay.services.filesService;

import com.iwomi.nofiaPay.core.utils.DateConverterUtils;
import com.iwomi.nofiaPay.dtos.UploadDto;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilesService implements IFilesService{
    private final ClientRepository clientRepository;

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
        return true;
    }

    private Boolean accountHistoryUpload(MultipartFile file) {
        return true;
    }
}
