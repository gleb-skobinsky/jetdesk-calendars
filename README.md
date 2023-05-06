# Simple date pickers for Compose Desktop

## This tiny library provides easy-to-use widgets for date and datetime picking in Compose Desktop:

1. Date range picker: allows to select a range of dates

**Usage:**
```kotlin
DateRangePicker { (startDate, endDate) ->
    println("$startDate $endDate")
}
```
Interface view:

![Date range picker](https://raw.githubusercontent.com/gleb-skobinsky/jetdesk-calendars/main/images/daterange.png)

2. Single date picker: allows to select a single date from the calendar

**Usage:**
```kotlin
DatePicker { (date) ->
    println(date)
}
```
Interface view:

![Date picker](https://raw.githubusercontent.com/gleb-skobinsky/jetdesk-calendars/main/images/date.png)

3. Single date-time picker: sorry, not yet implemented!
