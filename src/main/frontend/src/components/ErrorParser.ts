export class ErrorParser {

    public static parse (errorsObject: any) {
        let errorObject: any = {};
        errorsObject.forEach((e: any) => {
            errorObject[`${e.field}`] = e.defaultMessage;
        })
        return errorObject;
    }

}
