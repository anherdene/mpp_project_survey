import {uploadSurvey} from "../../api";

export class FileService {
    uploadFileToServer(file, title, desc){
        //returns Promise object
        console.log(file);
        return uploadSurvey(file, title, desc);
    }
}