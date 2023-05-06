# Simple date pickers for Compose Desktop

## This tiny library provides easy-to-use widgets for date and datetime picking in Compose Desktop:

1. Date range picker: allows to select a range of dates

**Usage:**
```kotlin
DateRangePicker { (startDate, endDate) ->
    println("$startDate $endDate")
}
```

2. Single date picker: allows to select a single date from calendar

**Usage:**
```kotlin
DatePicker { (date) ->
    println(date)
}
```

3. Single date-time picker: sorry, not yet implemented!
