import {uploadSurvey} from "../../api";

export class FileService {
    uploadFileToServer(file){
        //returns Promise object
        return uploadSurvey(file);
    }
}