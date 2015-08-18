WaitForTheInternet
==================

Usage
-----

```java
WaitForTheInternet
	.build(context)
    .setAction(new WaitForTheInternet.OnInternetAction() {
        @Override
        public void onInternet() {
            // DO SOMETHING WHEN INTERNET IS AVAILABLE;
        }
    })
    .execute();
```