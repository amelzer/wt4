import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

import { Provider } from 'react-redux';
import store from './store'

import Canvas from './components/Canvas';

class App extends Component {
  render() {
    return (
      <Provider store={store}>
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>WTFour?!</h2>
        </div>
        <Canvas />
      </div>
      </Provider>
    );
  }
}

export default App;
