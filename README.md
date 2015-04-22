# Switch-Button
A simple toggle switch

This is a switch toggle/button which have swipe gesture for changing the state of the button.

You can change the look by calling various methods and passing the color code you want to use.

About adding it to your existing project.

Download and add the library to your "libs" folder, or anywhere else to make it included in your build path.

Add the following lines in your XML

< com.itsrts.utility.SwitchButton
android:id="@+id/switchbutton"
android:layout_width="50dp"
android:layout_height="25dp" />

We prefer height to be half of the width, you may specify your own.

Get the reference in your java code and add the listener.

SwitchButton switchButton;
switchButton = (SwitchButton) findViewById(R.id.switchbutton);

// the following line will force you to implement an interface 

switchButton.setOnSwitchButtonStatusChangeListener(this);

// this is the method called on state change 
@Override
public void onSwitchButtonStatusChange(SwitchButton switchButton, boolean status) { // TODO you code here }

There are various other methods to get or set the status.

Happy Coding :)
