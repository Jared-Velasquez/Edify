export const monthPicker = (month: number): string => {
    const MONTHS = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    return MONTHS[month];
}

export const monthPickerShort = (month: number): string => {
    const MONTHS = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    return MONTHS[month];
}

export const dayPicker = (day: number): string => {
    const DAYS = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
    return DAYS[day];
}

export const dayPickerShort = (day: number): string => {
    const DAYS = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
    return DAYS[day];
}

export const dayNumberPickerSuffix = (day: number): string => {
    if (day % 10 === 1)
        return `${day.toString()}st`;
    else if (day % 10 === 2)
        return `${day.toString()}nd`;
    else if (day % 10 === 3)
        return `${day.toString()}rd`;
    else
        return `${day.toString()}th`;
}