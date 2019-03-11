import React, { Component } from 'react';
import {FileService} from "./FileService";
import Questions from "../Survey/Survey";

export class FileUploader extends Component {
    constructor(props) {
        super(props);
        this.state = {
            desc: this.props.desc,
            title: this.props.title
        };
        this.fileService = new FileService();
    }

    handleUploadFile = (event) => {
        const data = new FormData();
        //using File API to get chosen file
        let file = event.target.files[0];
        console.log("Uploading file", event.target.files[0]);
        data.append('file', event.target.files[0]);
        let title = this.state.title;
        let description = this.state.desc;
        // data.append('title', this.state.title);
        // data.append('description', this.state.desc);
        // let self = this;
        //calling async Promise and handling response or error situation
        this.fileService.uploadFileToServer(data, title, description).then((response) => {
            console.log("File " + file.name + " is uploaded");
        }).catch(function (error) {
            console.log(error);
            if (error.response) {
                //HTTP error happened
                console.log("Upload error. HTTP error/status code=",error.response.status);
            } else {
                //some other error happened
                console.log("Upload error. HTTP error/status code=",error.message);
            }
        });
    };

    render() {
        return (
            <div>
                <input type="file" onChange={this.handleUploadFile} />
            </div>
        )
    };
}