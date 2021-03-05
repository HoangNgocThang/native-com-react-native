import React from 'react';
import { requireNativeComponent, View } from 'react-native';
import PropTypes from 'prop-types';

const CircularProgressButton = (props) => (
    <RCTCircularProgressButton
        {...props}
        // style={props.style}
        // text={props.text}
        // completeText={props.completeText}
        // errorText={props.errorText}
        // progress={props.progress}
        // idleText={props.idleText}
    />
)

CircularProgressButton.propTypes = {
    text: PropTypes.string,
    completeText: PropTypes.string,
    errorText: PropTypes.string,
    idleText: PropTypes.string,
    progress: PropTypes.number,
    ...View.propTypes,
};

const RCTCircularProgressButton = requireNativeComponent('RCTCircularProgressButton');

export default CircularProgressButton