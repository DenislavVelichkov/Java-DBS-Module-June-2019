package softuni.jsonexer.service;

import softuni.jsonexer.domain.dto.binding.json.UserSeedDto;
import softuni.jsonexer.domain.dto.binding.xml.UserImportRootDto;
import softuni.jsonexer.domain.dto.views.json_views.UsersCountDto;

public interface UserService {

    void seedUsersFromJson(UserSeedDto[] userSeedDtos);

    UsersCountDto findAllSoldProductsByUser();

    void seedUsersFromXml(UserImportRootDto userImportRootDto);

}
