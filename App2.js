import React from "react";
import { SafeAreaView, Text, Image } from "react-native";
// import CircularProgressButton from './src/components/CircularProgressButton';
const imageExm = require('./src/assets/images/image.jpg');
import CustomView from './src/components/CustomView'

class App2 extends React.Component {

    state = {
        progress: 10000
    }

    render() {
        return (
            <SafeAreaView style={{ flex: 1 }}>
                <Text>Android Native UI Components</Text>
                <Image source={imageExm} style={{ width: 100, height: 100 }} resizeMode="contain" />
                {/* 
                <CircularProgressButton
                    style={{ height: 50, width: 100 }}
                    text="text"
                    completeText="Done"
                    errorText="Error"
                    idleText="idleText"
                    progress={this.state.progress} /> */}

                <CustomView style={{ height: 200, width: 200 }} />

            </SafeAreaView>
        )
    }

}

export default App2;