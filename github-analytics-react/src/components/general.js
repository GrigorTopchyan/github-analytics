import React from "react";
import {searchRepositories, getBookmarks, deleteBookmark, addBookmark, queryBookmarkIds} from '../actions/index'
import {Link} from "react-router-dom";

class General extends React.PureComponent {
    state = {
        queryString: '',
        repositories: []
    }

    handleChange = (e) => {
        e.preventDefault()
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    handleSearch = (e) => {
        e.preventDefault()
        const {queryString} = this.state
        searchRepositories(queryString).then(async repositories => {
            const bookmarkIds = await queryBookmarkIds()
            repositories.map(repo => {
                repo.bookmark = bookmarkIds.includes(repo.id);
                return repo
            })
            this.setState({
                repositories
            })
        }).catch(err => {
            this.setState({
                repositories: null
            })
        })
    }

    handleGetBookmarks = (e) => {
        e.preventDefault()
        getBookmarks().then(repositories => {
            repositories.map(repo => {
                repo.bookmark = true;
                return repo
            })
            this.setState({
                repositories
            })
        })
    }

    toggleBookmark = (e, repository) => {
        let {repositories} = this.state
        repositories = repositories.map(repo => {
            if (repo.id === repository.id) {
                repo.bookmark = e.target.checked
            }
            return repo
        })
        if (e.target.checked) {
            addBookmark(repository).then(() => {
                this.setState({
                    repositories
                })
            })
        } else {
            deleteBookmark(repository.id).then(() => {
                this.setState({
                    repositories
                })
            })
        }
    }
    render() {
        const {repositories} = this.state

        return (
            <div className="col">
                <div className="d-flex justify-content-between mt-4">
                    <div className='w-50 input-group input-group-merge p-0'>
                        <input
                            type='text'
                            className='form-control'
                            placeholder='Search'
                            name='queryString'
                            onChange={e => this.handleChange(e)}
                            id='template-search-id'
                        />
                        <div className='input-group-prepend cursor-pointer' onClick={e => this.handleSearch(e)}>
                            <div className='input-group-text'>
                                <span className='fe fe-search'></span>
                            </div>
                        </div>
                    </div>
                    <div>
                        <button className="btn btn-primary" onClick={e => this.handleGetBookmarks(e)}>Bookmarks</button>
                    </div>
                </div>
                <div className="mt-5">
                    {
                        !repositories || repositories.length === 0 ?
                            <div className="alert alert-light p-4 m-5 text-center">
                                <h1 className='p-0 m-0'>No repositories to show</h1>
                            </div>
                            :
                            <div className="card">
                                <div className="card-body p-3">
                                    <div className="table-responsive">
                                        <table className="table mb-0 table-checkbox">
                                            <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Github Link</th>
                                                <th>Description</th>
                                                <th>Created</th>
                                                <th>Bookmark</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {
                                                repositories.map(repository => {
                                                    return <tr key={repository.id}>
                                                        <td><Link
                                                            to={`/repository/${repository.id}`}>{repository.full_name}</Link>
                                                        </td>
                                                        <td>{repository.html_url}</td>
                                                        <td>{repository.description}</td>
                                                        <td>{repository.created_at}</td>
                                                        <td>
                                                        <input type="checkbox" checked={!!repository.bookmark} className="checkbox-sm" onChange={e => this.toggleBookmark(e, repository)}/>
                                                        </td>
                                                    </tr>
                                                })
                                            }
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                    }
                </div>
            </div>
        )
    }

}

export default General
