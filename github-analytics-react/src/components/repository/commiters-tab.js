import React from "react";
import {getCollaborators} from '../../actions/index'

class CommitersTab extends React.PureComponent {
    state = {
        commiters:  [],
    }

    componentDidMount() {
        getCollaborators(this.props.repositoryId).then(commiters => {
            this.setState({
                commiters
            })
        }).catch(err => {
            // this.setState({
            //     commiters: null
            // })
        })
    }


    render() {
        const {commiters} = this.state
        if (!commiters || commiters.length === 0) {
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
                        </tr>

                        </thead>
                        <tbody>
                        {
                            commiters.map(commiter => {
                                return <tr key={commiter.id}>
                                    <td>{commiter.id}</td>
                                    <td>{commiter.login}</td>
                                    <td>
                                        <a href={commiter.url} target='_blank' rel='noopener noreferrer'>{commiter.url}</a>
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

export default CommitersTab
