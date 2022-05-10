export function createDropdownOptions(data: any, fieldLabel?: string) {
    return data.map((item: any) => {
        const labelValue: string = (fieldLabel) ? item[fieldLabel] : item;
        return { value: item, label: labelValue };
    });
}