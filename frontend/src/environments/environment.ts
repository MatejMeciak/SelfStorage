// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  OAUTH2_URL: 'http://localhost:8080/api/' + 'oauth2/authorization/',
  REDIRECT_URL: '?redirect_uri=http://localhost:4200/login',
  AUTH_API: 'http://localhost:8080/api/' + 'auth/',
  GOOGLE_AUTH_URL: 'http://localhost:8080/api/oauth2/authorization/' + "google" + '?redirect_uri=http://localhost:4200/login',
  FACEBOOK_AUTH_URL: 'http://localhost:8080/api/oauth2/authorization/' + "facebook" + '?redirect_uri=http://localhost:4200/login',
};



/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
