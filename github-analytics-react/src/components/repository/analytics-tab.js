import React from "react";
import {queryAnalytics} from "../../actions";

class AnalyticsTab extends React.PureComponent {
    state = {
        analytics: []

    }

    componentDidMount() {
        queryAnalytics(this.props.repositoryId).then(analytics => {
            this.setState({
                analytics
            })
        })
    }

    render() {
        const {analytics} = this.state
        if (!analytics || analytics.length === 0) {
            return <h1 className='text-center'>Not found</h1>
        }
        return (
            <div className="card">
                <div className="table-responsive">
                    <table className="table table-sm table-nowrap card-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Login</th>
                            <th>Url</th>
                            <th>User Impact</th>
                        </tr>

                        </thead>
                        <tbody>
                        {
                            analytics.map(analytic => {
                                const {author} = analytic
                                return <tr key={author.id + analytic.user_impact}>
                                    <td>{author.id}</td>
                                    <td>{author.login}</td>
                                    <td>
                                        <a href={author.url} target='_blank' rel='noopener noreferrer'>{author.url}</a>
                                    </td>
                                    <td>{analytic.user_impact} %</td>
                                </tr>
                            })
                        }
                        </tbody>
                    </table>
                </div>
            </div>

        )
    }
}

export default AnalyticsTab
