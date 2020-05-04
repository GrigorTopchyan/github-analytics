import React from "react";

function Details(props) {
    return (
        <div className="card">
            <div className="table-responsive">
                <table className="table table-sm table-nowrap table-bordered card-table">
                    <thead>
                    <tr>
                        <th className='w-25'>ID</th>
                        <td>{props.repository.id}</td>
                    </tr>
                    <tr>
                        <th className='w-25'>Name</th>
                        <td>{props.repository.full_name}</td>
                    </tr>
                    <tr>
                        <th className='w-25'>Github Link</th>
                        <td>
                            <a href={props.repository.html_url} rel="noopener noreferrer"  target='_blank'>{props.repository.html_url}</a>
                        </td>
                    </tr>
                    <tr>
                        <th className='w-25'>url</th>
                        <td>
                            <a href={props.repository.url} rel="noopener noreferrer"  target='_blank'>{props.repository.url}</a>
                        </td>
                    </tr>
                    <tr>
                        <th className='w-25'>Description</th>
                        <td>{props.repository.description}</td>
                    </tr>
                    <tr>
                        <th className='w-25'>Clone url</th>
                        <td>{props.repository.clone_url}</td>
                    </tr>
                    <tr>
                        <th className='w-25'>Created</th>
                        <td>{props.repository.created_at}</td>
                    </tr>
                    <tr>
                        <th className='w-25'>Updated</th>
                        <td>{props.repository.updated_at}</td>
                    </tr>
                    <tr>
                        <th className='w-25'>Pushed</th>
                        <td>{props.repository.pushed_at}</td>
                    </tr>
                    {
                        props.repository.language &&
                        <tr>
                            <th className='w-25'>Language</th>
                            <td>{props.repository.language}</td>
                        </tr>
                    }
                    </thead>
                </table>
            </div>
        </div>
    )
}

export default Details
