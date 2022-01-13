const apiBaseUrl = 'http://localhost:8080/';
const apiUrl = apiBaseUrl + 'api/';
const authApi = apiUrl + 'auth/';
const oauth2Url = apiBaseUrl + 'oauth2/authorization/';
const redirectUrl = '?redirect_uri=http://localhost:4200/login';

export const environment = {
  production: false,
  apiUrl: apiUrl,
  authApi: authApi,
  googleAuthUrl: oauth2Url + 'google' + redirectUrl,
  facebookAuthUrl: oauth2Url + 'facebook' + redirectUrl,
};
