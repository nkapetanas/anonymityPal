export function createDropdownOptions(data: any) {
    return data.map((item: any)=> {
        return { value: item, label: item };
    });
}