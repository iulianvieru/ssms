import React, { Component } from "react";
import UploadService from "../services/upload-files.service";

export default class UploadFiles extends Component {
    constructor(props) {
        super(props);
        this.selectFile = this.selectFile.bind(this);
        this.upload = this.upload.bind(this);

        this.state = {
            selectedFiles: undefined,
            currentFile: undefined,
            progress: 0,
            message: "",

            fileInfos: [],
        };
    }



    selectFile(event) {
        this.setState({
            selectedFiles: event.target.files,
        });
    }

    upload() {
        let currentFile = this.state.selectedFiles[0];

        this.setState({
            progress: 0,
            currentFile: currentFile,
        });

        UploadService.upload(currentFile, (event) => {
            this.setState({
                progress: Math.round((100 * event.loaded) / event.total),
            });
        })
            .then((response) => {
                this.setState({
                    message: response.data.message,
                });
                return UploadService.getFiles();
            })
            .then((files) => {
                this.setState({
                    fileInfos: files.data,
                });
            })
            .catch(() => {
                this.setState({
                    progress: 0,
                    message: "Could not upload the file!",
                    currentFile: undefined,
                });
            });

        this.setState({
            selectedFiles: undefined,
        });
    }

    render() {
        const {
            selectedFiles,
            currentFile,
            progress,
            message,
            fileInfos,
        } = this.state;

        return (
            <div className="ui placeholder segment">
                <div className="ui form ">
                    <div className="ui field">
                        {currentFile && (
                            <div className="progress">
                                <div
                                    className="progress-bar progress-bar-info progress-bar-striped"
                                    role="progressbar"
                                    aria-valuenow={progress}
                                    aria-valuemin="0"
                                    aria-valuemax="100"
                                    style={{ width: progress + "%" }}
                                >
                                    {progress}%
                                </div>
                            </div>
                        )}
                    </div>
                    <div className="ui field">
                        {/* <label className="btn btn-default"> */}
                            <input type="file" onChange={this.selectFile} />
                        {/* </label> */}
                    </div>
                    <div className="ui field"></div>    
                        <button
                            className="ui blue submit button"
                            disabled={!selectedFiles}
                            onClick={this.upload}
                        >
                            Upload
                        </button>
                    </div>
                    <div className="ui field">       
                        <div className="alert alert-light" role="alert">
                            {message}
                        </div>
                    </div>


                </div>
                        
        );
    }
}
