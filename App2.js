import React from "react";
import { SafeAreaView, Text, requireNativeComponent, Image } from "react-native";
const imageExm = require('./src/assets/images/image.jpg');

class App2 extends React.Component {

    render() {
        return (
            <SafeAreaView style={{ flex: 1 }}>
                <Text>aaa</Text>
                <Image source={imageExm} style={{ width: 100, height: 100 }} resizeMode="contain" />
            </SafeAreaView>
        )
    }

}

export default App2;