Contents
This is the sample to do the searching of the company name in the searchbar from the list of the contacts.

For JAVA classes:
1. As the app opens I have created a SplashActivity whichtakes you in the application to its MainActivity.
2. MainActivity is having a list of the contacts in which the info is given.
-To fetch the information of the contacts the HTTP service is called to fetch the data from the URL.
-AutoCompleteTextView is used to do the search.
3.CONTACTS, POJO is a getter and setter class created to save the data coming from the service. In this data is set when
it comes from the server and then it is called in the adapter to get the data.
4.Adapter folder is created in that contactAdapter is created to set the view of the list. Adapter is implemented by the
Filterable so that its method will be implemented and the search list can be shown.
5.ContactFilter is the class where the filteration is happening. It is extended by Filter class.The filter is on the basis
of the CompanyName.


For XML:
1. MainActivity view it is having AutocompleteTextView and the ListView in the parent RelativeLayout.
2.SplashActivity is having the view of just textView which says the application name and the background color.
3.Adapter is having the views of textViews in the parent relativeLayout.
4.Used background file for the search textView that is placed in the drawables.

-- All the values are placed in the respective folders as per their values that is either colors,dimens and strings.
No hard cord values are given in the application.


How to run a sample

Use this utility, which will automatically create them for you.
Building using Android Studio...
Open Android Studio and launch the Android SDK manager from it (Tools | Android | SDK Manager)
Ensure the following components are installed and updated to the latest version.
Android SDK Platform-Tools
Android Support Repository
Return to Android Studio and select Open an existing Android Studio project
Modify IDs, compile and run

Building
To build the samples after you have applied the changes above, you can use the build/run option in Android Studio, or build directly from the command line if you prefer.
IMPORTANT Ensure you have set the ANDROID_HOME environment variable.

cd /path/to/android-basic-samples
export ANDROID_HOME = /path/to/android/sdk
./gradlew build


For Testing:
I have used debugger and the Logs.
Logs I have removed just from the clean code perspective.
Did manual unit testing for that I have created the following cases while developing and testing:

1. I have checked all the keys coming in the response. As per the data is set.
2. Checked the searchList on the basis of the companyName.

