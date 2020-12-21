import React from 'react';
import { connect } from 'react-redux';
import { fetchStudents } from '../actions';
import Moment from 'moment';



class StudentList extends React.Component {
    componentDidMount() {
        this.props.fetchStudents();
    }

    renderList() {

        return this.props.data.students.map(student => {
            return (
                <tr className="row" key={student.id}>
                    <td className="col-sm-2">{student.firstName}</td>
                    <td className="col-sm-2">{student.lastName}</td>
                    <td className="col-sm-2">{Moment(student.birthDate).format("yyyy-MM-DD")}</td>

                </tr>

            );
        });
    }
    loadPosts() {
        const { page, limit } = this.props.data;
        this.props.postsActions.fetchPosts(page, limit);
    }

    render() {
        return <div className="container">
            <table className="ui celled striped table">
            <thead>              <tr>
                    <th className="col-sm">First Name</th>
                    <th className="col-sm">Last Name</th>
                    <th className="col-sm">Birth date</th>

                </tr>
                </thead>
  
                <tbody>
                    {this.renderList()}
                </tbody>
            </table>

        </div>;
    }
}

const mapStateToProps = state => {
    console.log("StudentList.mapToStateProps():props=", state);
    return { data: state.students };
};

export default connect(
    mapStateToProps,
    { fetchStudents }
)(StudentList);