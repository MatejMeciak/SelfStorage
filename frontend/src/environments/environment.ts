// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
const apiBaseUrl = 'http://localhost:8080/';
const apiUrl = apiBaseUrl + 'api';
const authApi = apiUrl + '/auth/';
const oauth2Url = apiBaseUrl + 'oauth2/authorization/';
const redirectUrl = '?redirect_uri=http://localhost:4200/login';

export const environment = {
  production: false,
  apiUrl: apiUrl,
  authApi: authApi,
  googleAuthUrl: oauth2Url + 'google' + redirectUrl,
  facebookAuthUrl: oauth2Url + 'facebook' + redirectUrl,
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
