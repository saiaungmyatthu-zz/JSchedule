# JSchedule
Java GUI application with excel upload for 8hr work schedule allocation. The application is used for managing [monthly] work allocation of [24/7 eight hour schedule] from day [1-28].

# JSchedule-Service
Java library to support data retrieve and excel data upload for [JSchedule] GUI application.
Used MyBatis OR Mapping for relational database CRUD operation and apache POI for excel upload for data input.

# Prerequisites
1. Eclipse with JavaFX library
2. MySQL Workbench
3. JavaFX Scene Builder 2.0
4. Java 1.8
5. MyBatis
6. Apache Ant

# How to setup?
1. Create a folder "C:\jschedule\lib" and copy dependency jar files from [lib] folder.
2. Execute sql script from [db] folder.
3. Download JSchedule-Service & JSchedule project archives and extract the zip file.
4. // add ant build script steps

# Things to do
Following picture shows the table relationship between shift, shift_group and shift_grouping.</br>
shift and shift_group has many-many relationship and shift_grouping table is a reference table.

shift         : consists data for type of shift like day shift, night shift, etc.</br>
shift_group   : consists data for group info to work for specific shift like Team 1, Team 2, etc.</br>
shift_grouping: reference table for schedule one-many relationship with shift and shift_group by shift_code and shift_group_code.</br>
![alt text](https://github.com/saiaungmyatthu/JSchedule/blob/master/images/pic1.png)

# How JSchedule application work?
1. How schedule data upload to application?</br>
   The application uses Apache POI library to support both xls & xlsx excel upload.</br>
   User must use the template excel(work_allocation.xlsx) provided in [data] folder.</br>
   The sample excel data as below.</br>
   ![alt text](https://github.com/saiaungmyatthu/JSchedule/blob/master/images/sample_data.png)</br>
   
   Following are the screenshot how to upload data to application.</br>
   ![alt text](https://github.com/saiaungmyatthu/JSchedule/blob/master/images/p2.png)</br>
   ![alt text](https://github.com/saiaungmyatthu/JSchedule/blob/master/images/p3.png)</br>
   ![alt text](https://github.com/saiaungmyatthu/JSchedule/blob/master/images/p1.png)</br>
   
2. How to validate schedule by shift_code and shift_group?</br>
   To validate schedule by shift_code and shift_group user must provide Shift Code, Shift Group Code and specific day(optional).</br>
   Upon clicking "Verify Schedule" button application will do mandatory field check first then try to fetch the total number of schedule data records in table.</br>
   If no record then verify fail otherwise success.</br>
   
   Following are the screenshot for schedule validation.</br>
   ![alt text](https://github.com/saiaungmyatthu/JSchedule/blob/master/images/p5.png)</br>
   ![alt text](https://github.com/saiaungmyatthu/JSchedule/blob/master/images/p4d.png)</br>
   
