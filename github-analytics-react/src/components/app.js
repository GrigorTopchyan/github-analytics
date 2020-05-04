import React, {Component} from 'react';
import {Router, Route, Redirect} from 'react-router-dom';
import history from '../history';
import LogIn from './log-in'
import General from "./general";
import Repository from "./repository/repository";

const PrivateRoute = ({component: Component, ...rest}) => {
    return (
        <Route
            {...rest}
            exact={true}
            render={props => {
                return localStorage.getItem('jwtToken') ? <Component {...props} /> : <Redirect to='/login'/>;
            }}
        />
    );
};

class App extends Component {
    render() {
        return (
            <Router history={history}>
                <div>
                    <Route path='/login' component={() => <LogIn/>}/>
                    <PrivateRoute path='/' component={() => <General/>}/>
                    <PrivateRoute path='/repository/:id' component={() => <Repository/>}/>
                </div>
            </Router>
        );
    }
}

export default App;
