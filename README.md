# **alexandra-1570-a5**
## Requirements
The project is an Inventory which satisfies these requirements:
1. The user shall interact with the application through a Graphical User Interface
2. The user shall be able to store at least 100 inventory items
   1. Each inventory item shall have a value representing its monetary value in US dollars
   2. Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit 
   3. Each inventory item shall have a name between 2 and 256 characters in length (inclusive)
3. The user shall be able to add a new inventory item 
4. The user shall be able to remove an existing inventory item 
5. The user shall be able to edit the value of an existing inventory item 
6. The user shall be able to edit the serial number of an existing inventory item 
7. The user shall be able to edit the name of an existing inventory item
8. The user shall be able to sort the inventory items by value
9. The user shall be able to sort inventory items by serial number
10. The user shall be able to sort inventory items by name
11. The user shall be able to search for an inventory item by serial number
12. The user shall be able to search for an inventory item by name
13. The user shall be able to save their inventory items to a file
14. The user shall be able to load inventory items from a file that was previously created by the application.

## How The Project Works
When the program runs, a window will pop up. This window is the gui of the program, which contains: 
1. A MenuBar
2. 3 TextFields on the Left Corner and the Add Button
3. A table
4. 3 Checkboxes below the table
5. A remove Button
6. Search TextField

### MenuBar 
The MenuBar consists of 2 elements.
1. Save
2. Open

These two when clicked, will have 3 more choices which are 
1. Json 
2. TSV
and
3. HTML 

When a User clicks on any choices in the Save MenuBar, a Pop Up window will come out where there is a textfield. 
This pop up window is used such that the User can type in the name and location of the file to be saved.

If a user clicks on Save Json/TSV/HTML without the ending .json/.txt/.html, the program will immediately add it. 
If nothing is entered, the Program will warn the user that nothing was entered. 

This idea is used for the Open command in the MenuBar. A pop up window will allow the user to write the name and location of the file they'd like to open. 

###TextFields and Table
The TextFields on the Top Left is used so the User can add data to the table.

In this Inventory Program, the user shall add 3 properties of items added to the inventory: 
1. Value 
2. Serial Number
3. Name

The **Value** is in 2 decimal places and has a dollar sign in front of it.
The **Serial Number** is unique to the item itself.
The **Name** must be between 2 and 256 character length.

Once the User has entered the data information in the right format and clicks the Add button which is located below the 3 textfields, the data entered will be added to the Table.
The correct format of each is
**Value** must be in decimal form. 
**SerialNumber** must be unique, 10 character long and alphanumeric.
**Name** must have the valid length.

The Benefit of using Tables allows me to easily set cells as editable. If new edited cells does not satisfy the format for each item properties, an alert box will alert the User. 

###3 Checkboxes
The Checkboxes below the table allows user to sort the table based on Value, Serial Number or Name. 
Although Tables can do this by clicking the heading of each columns, I thought that having them in checkboxes are more intuitive. 

###Remove Button
This Remove Button allows user to delete a row of data in the Table. 
The User must select a row in the table first before clicking on the button.

###Search TextField
This Textfield allows user to be able to Search an item in the Table 
The search is only based on Name and Serial number. 

Once the value written in the TextField matches with the Name or SerialNumber of an item, only that item will be seen in the table.