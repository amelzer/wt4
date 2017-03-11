import React, { Component } from 'react';
import { connect } from 'react-redux';

import './style.css';

import TopView from './../TopView';
import FrontView from './../FrontView';
import GameInfo from './../GameInfo';

import * as actions from './../../store/actions';

class Canvas extends Component {

  constructor (props) {
    super(props);
    this.state = {
      hover: props.showFrontView ? 'flip' : ''
      //hover: 'flip'
    };
  }

  componentDidMount() {
    this.props.createNewGame();
  }

  render () {
    return (
      <div>
      <GameInfo/>
        <div className={"flip-container "+this.state.hover}>
          <div className="flipper">

            <div className='canvas front' style={{height: this.props.height, width: this.props.width}}>
              <TopView />
              <TopView hoverArray />
            </div>

            <div className='canvas back' style={{height: this.props.height, width: this.props.width}}>
              <FrontView />
            </div>

          </div>
        </div>

      </div>
    )
  }
}


const mapStateToProps = (state) => {
  return {
    width: state.gameSettings.canvasWidth,
    height: state.gameSettings.canvasHeight,
    showFrontView: state.gameState.showFrontView
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    createNewGame: () => dispatch(actions.createNewGame())
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Canvas);

