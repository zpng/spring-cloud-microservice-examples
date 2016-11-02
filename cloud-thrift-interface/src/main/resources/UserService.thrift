namespace java cloud.thrift

struct UserDto {
  1: i32 id
  2: string username
}

service UserService {
  UserDto getUser()
}