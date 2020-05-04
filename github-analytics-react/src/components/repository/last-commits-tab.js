import React from "react";
import {queryLastCommits} from "../../actions";

class LastCommitsTab extends React.PureComponent {
    state = {
        lastCommits: []
    }

    componentDidMount() {
        queryLastCommits(this.props.repositoryId).then(lastCommits => {
            this.setState({
                lastCommits
            })
        })
    }

    render() {
        const {lastCommits} = this.state
        if (!lastCommits || lastCommits.length === 0) {
            return <h1 className='text-center'>Not found</h1>
        }

        return (
            <div className="card">
                <div className="table-responsive">
                    <table className="table table-sm table-nowrap card-table">
                        <thead>
                        <tr>
                            <th>Author</th>
                            <th>Message</th>
                            <th>Date</th>
                            <th>Url/Sha</th>
                        </tr>

                        </thead>
                        <tbody>
                        {
                            lastCommits.map(lastCommit => {
                                const {author} = lastCommit
                                return <tr key={lastCommit.sha}>
                                    <td>
                                        {author.id} / {author.login} <br />
                                        <a href={author.url} target='_blank' rel='noopener noreferrer'>{author.url}</a>
                                    </td>
                                    <td>{lastCommit.message}</td>
                                    <td>{lastCommit.date}</td>
                                    <td>
                                        <a href={lastCommit.url} target='_blank' rel='noopener noreferrer'>{lastCommit.sha}</a>
                                    </td>
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

export default LastCommitsTab
