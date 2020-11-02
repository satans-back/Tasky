# Tasky

Tasky is a simple app created for "Programowanie Aplikacji Mobilnych" course in my university.
It's main features are to enable user to create simple task with stages: To do, In Progress, Done with possible transitions between each other stages.
It has been created with Material Design official standards and library has been included in `build.gradle`


# Trying to run it?

- Requirements:
`dependencies {  
	  implementation 'androidx.appcompat:appcompat:1.2.0'  
	  implementation 'com.google.android.material:material:1.2.1'  
	  implementation 'androidx.constraintlayout:constraintlayout:2.0.2'  
	  implementation 'androidx.drawerlayout:drawerlayout:1.0.0'  
	  implementation 'com.android.support:support-v4:28.0.0'  
	  implementation 'androidx.navigation:navigation-fragment:2.3.1'  
	  implementation 'androidx.navigation:navigation-ui:2.3.1'  
	  implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.2.0'  
	  implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'  
	  implementation 'androidx.legacy:legacy-support-v4:1.0.0'  
	  testImplementation 'junit:junit:4.+'  
	  androidTestImplementation 'androidx.test.ext:junit:1.1.2'  
	  androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
	  }` 	

## Architecture

It's been created with Navigation Drawer activity which holds four fragments:

    1. nav_to_do
    2. nav_in_progress
    3. nav_done
    4. nav_task

## Interfaces

I've also learned how to create interfaces in Java for fragments to interact with each other.

## Screenshots
