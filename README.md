# GithubSearch

1. Based on MVP pattern, MainContract.java defined Presenter and View interface for the main page, and FollowerContract.java defined the Presenter and View interface for the followers details page.

2. Used OKHttp for async HTTP calls to the Github APIs, to avoid blocking the UI when process HTTP operations.

3. A callback interface is defined for the async HTTP mechanics, when HTTP operations completed, the presenters will be informed to get the data by the callback, and in turn, the presenters will update the view (display data from API calls).

4. Used Fresco for online image displaying.

5. Compile and target SDK version is 27.

6. An apk for testing is put at ../apk/app-debug.apk

7. The behavior of the magnifier icon on the action bar of Home page was not defined, so I ignored it.
