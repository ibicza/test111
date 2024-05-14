[RU]
Эта служба отвечает за аутентификацию пользователя.
Аутентификация организована на основе токенов JWT, включая токены доступа и токены обновления (используются в случаях, когда срок действия токена доступа истек).
Токены хранятся в той же базе данных, что и пользователь.

После запуска службы она создает базу данных для хранения пользовательской информации.</br>в
Если вы хотите удалить эту функцию, отключите метод init() в [AuthenticationServiceApplication.java].

[EN]
This service is responsible for user authentication. 
Authentication is organised based on JWT tokens, include access tokens and refresh tokens(used in cases when access token is expired).</br>
Tokens stored in the same database as user. 

After service is launched it creates a database to store user information.</br>
If you want to remove this feature, disable init() method in the [AuthenticationServiceApplication.java].

Default users for testing:</br> 1) admin, pass </br> 2) user, pass

Tokens include "sub"(username), "id", "roles"(user roles represented as role names(string)) "iat"(issued at), "exp"(token expiration date).

-Access token lifetime - 1 day

-Refresh token lifetime - 30 days

Base URL: http://localhost:50500/api/auth

Rest methods:

**POST:** **/authenticate** </br>
request (json) :
`{"username": "user", "password" : "pass" }` </br>
response (json) : `{"accessToken" : "token value", "refreshToken" : "token value"} `

**POST:** **/refreshTokens**</br>
request(json) : `{"refreshToken" : "token value" }`</br>
response(json) : `{"accessToken" : "token value", "refreshToken" : "token value"}`

Consider that both methods return tokens in the response body as well as in the cookie.
