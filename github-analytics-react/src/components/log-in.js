import React from 'react'
import history from "../history";
import {signIn} from '../actions'
import $ from 'jquery'

class LogIn extends React.PureComponent {

    state = {
        username: "",
        password: "",
        loginError: false,
        errorMessage: null
    }

    componentDidMount() {
        $('#signInFocus').focus()
    }

    handleChange = (e) => {
        this.setState({[e.target.name]: e.target.value});
    }

    handleSubmit = (e) => {
        e.preventDefault()
        signIn(this.state.username, this.state.password).then(() => {
            history.push('/')
        }).catch(err => {
            this.setState({loginError: true, errorMessage: err.message})
        });
    }


    renderSignInError = () => {
        if (this.state.loginError) {
            return (
                <div className="alert alert-danger text-center" role="alert">
                    {this.state.errorMessage}
                </div>
            )
        }
    }

    render() {
        return (
            <div style={{
                backgroundImage: 'url(img/sign-in-bg.jpg)',
                backgroundRepeat: "no-repeat",
                backgroundSize: "cover"
            }}>
                <div className="container">
                    <div className="row justify-content-center align-items-center" style={{height: '100vh'}}>
                        <div className="authentication-block col-12 border border-primary col-md-6 px-5 py-4">
                            <h2 className="text-center mb-3">
                                Log In
                            </h2>
                            <form onSubmit={e => this.handleSubmit(e)}>
                                <div className="form-group">
                                    <input type="text" name='username' value={this.state.email}
                                           onChange={e => this.handleChange(e)}
                                           id='signInFocus'
                                           required={true}
                                           className="form-control form-control-white"
                                           placeholder="Enter your email or username"/>
                                </div>
                                <div className="form-group">
                                    <div className="input-group input-group-merge">
                                        <input type="password" name='password' value={this.state.password}
                                               onChange={e => this.handleChange(e)}
                                               required={true}
                                               className="form-control form-control-white"
                                               placeholder="Enter your password"/>
                                    </div>
                                </div>
                                {this.renderSignInError()}
                                <input type="submit" value="Log in" className="btn btn-block btn-primary mb-3"/>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        )
    }
}


export default LogIn;
