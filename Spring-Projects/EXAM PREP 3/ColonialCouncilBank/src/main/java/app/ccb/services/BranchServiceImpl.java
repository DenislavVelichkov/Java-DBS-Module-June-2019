package app.ccb.services;

import app.ccb.domain.dtos.json.BranchSeedDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.GsonParser;
import app.ccb.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BranchServiceImpl implements BranchService {
    private final static String BRANCHES_JSON_FILE_PATH =
            System.getProperty("user.dir") +
                    "/src/main/resources/files/json/branches.json";

    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final GsonParser gsonParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, GsonParser gson, GsonParser gsonParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.gsonParser = gsonParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() != 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return this.fileUtil.readFile(BRANCHES_JSON_FILE_PATH);
    }

    @Override
    public String importBranches(String branchesJson) {
        StringBuilder sb = new StringBuilder();
        BranchSeedDto[] branchSeedDtos =
                this.gsonParser.parseDataFromJson(branchesJson, BranchSeedDto[].class);

        for (BranchSeedDto branchDto : branchSeedDtos) {
            if (!this.validationUtil.isValid(branchDto)) {
                System.out.println("Error: Incorrect Data!");

                continue;
            }

            Branch branch =
                    this.branchRepository
                            .findByName(branchDto.getName())
                            .orElse(null);

            if (branch == null) {
                branch = this.modelMapper.map(branchDto, Branch.class);
                this.branchRepository.saveAndFlush(branch);
                sb.append(String.format("Successfully imported Branch – %s.", branchDto.getName()))
                        .append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
