import React from "react";
import {queryRepository} from '../../actions'
import {Link, withRouter} from "react-router-dom";
import 'bootstrap'
import Details from "./details-tab";
import CommitersTab from "./commiters-tab";
import AnalyticsTab from "./analytics-tab";
import LastCommitsTab from "./last-commits-tab";

class Repository extends React.PureComponent {
    state = {
        repository: null,
    }


    componentDidMount() {
        queryRepository(this.props.match.params.id).then(repository => {
            this.setState({
                repository
            })
        }).catch(err => {
            // this.setState({
            //     errMessage: err.message,
            //     repository: null
            // })
        })
    }

    render() {
        const {repository, errMessage} = this.state
        if (!repository || repository.length === 0) {
            return <div className="alert alert-light p-4 m-5 text-center">
                <h1 className='p-0'>Repository not found</h1>
                <Link to='/'>Repositories</Link>
            </div>
        }

        return (
            <div className='col'>
                {
                    errMessage &&
                    <div className="alert alert-danger p-4 m-5">
                        <h1 className='text-center p-0 m-0'>{errMessage}</h1>
                    </div>
                }
                <div className="header">
                    <nav aria-label="breadcrumb" className='my-3'>
                        <ol className="breadcrumb py-0">
                            <li className="breadcrumb-item">
                                <Link to='/'>Repositories</Link>
                            </li>
                            <li className="breadcrumb-item active" aria-current="page">
                                {this.props.match.params.id}
                            </li>
                        </ol>
                    </nav>
                    <div className="header-body">
                        <div className="row">
                            <div className="col">
                                <ul className="nav nav-tabs header-tabs" id="pills-tab" role="tablist">
                                    <li className="nav-item">
                                        <a className="nav-link active pt-0 pb-2" id="tabs-list-details-tab"
                                           data-toggle="tab"
                                           href="#tabs-details" role="tab"
                                           aria-controls="pills-home" aria-selected="true">Details</a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link pt-0 pb-2" id="tabs-commiters-tab" data-toggle="tab"
                                           href="#tabs-commiters" role="tab"
                                           aria-controls="pills-profile" aria-selected="false">Commiters</a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link pt-0 pb-2" id="tabs-analytics-tab"
                                           data-toggle="tab"
                                           href="#tabs-analytics" role="tab"
                                           aria-controls="pills-home" aria-selected="true">Analytics</a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link pt-0 pb-2" id="tabs-latest-commits-tab"
                                           data-toggle="tab"
                                           href="#tabs-latest-commits" role="tab"
                                           aria-controls="pills-home" aria-selected="true">Latest Commits</a>
                                    </li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
                <div className="tab-content mt-3" id="tabs-tabContent">
                    <div
                        className="tab-pane fade show active"
                        id="tabs-details"
                        role="tabpanel"
                        aria-labelledby="tabs-list-details-tab"
                    >
                        <Details repository={repository} />
                    </div>
                    <div
                        className="tab-pane fade"
                        id="tabs-commiters"
                        role="tabpanel"
                        aria-labelledby="tabs-commiters-tab"
                    >
                        <CommitersTab repositoryId={repository.id}/>
                    </div>
                    <div
                        className="tab-pane fade"
                        role="tabpanel"
                        id="tabs-analytics"
                        aria-labelledby="tabs-analytics-tab"
                    >
                       <AnalyticsTab repositoryId={repository.id}/>
                    </div>
                    <div
                        className="tab-pane fade"
                        role="tabpanel"
                        id="tabs-latest-commits"
                        aria-labelledby="tabs-latest-commits-tab"
                    >
                        <LastCommitsTab repositoryId={repository.id} />
                    </div>
                </div>
            </div>
        )
    }
}

export default withRouter(Repository)
