import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  TouchableOpacity,
  Alert,
} from 'react-native';

import { Colors, } from 'react-native/Libraries/NewAppScreen';
import { NativeModules, Button, NativeEventEmitter } from 'react-native';

class App extends React.Component {

  componentDidMount() {
    const eventEmitter = new NativeEventEmitter(NativeModules.MyFirebaseMessagingServiceModule);
    this.eventSendMessageListener = eventEmitter.addListener('onReceivedSendMessageEvent', (event) => {
      console.log('vaoday')
      console.log(event)
      Alert.alert(JSON.stringify(event));
    });

    this.eventSendNoticationListener = eventEmitter.addListener('onReceivedSendNotificationEvent', (event) => {
      console.log('vaoday')
      console.log(event)
      Alert.alert(JSON.stringify(event));
    });

  }

  componentWillUnmount() {
    this.eventSendMessageListener.remove();
    this.eventSendNoticationListener.remove();
  }

  onClick = () => {
    const { MyFirebaseMessagingServiceModule } = NativeModules;
    MyFirebaseMessagingServiceModule.onClickEvent();
  }

  onClick2 = async () => {
    try {
      const { MyFirebaseMessagingServiceModule } = NativeModules;
      const res = await MyFirebaseMessagingServiceModule.onClickSendParamsEvent("thang");
      alert(res);
    } catch (error) {
      console.error(e);
    }
  }

  onClick3 = async () => {
    try {
      const { MyFirebaseMessagingServiceModule } = NativeModules;
      const res = await MyFirebaseMessagingServiceModule.getTokenFCM();
      console.log("HHHHHHH:", res)
      alert(res);
    } catch (error) {
      console.error(e);
    }
  }

  onClick4 = async () => {
    try {
      const { MyFirebaseMessagingServiceModule } = NativeModules;
      const res = await MyFirebaseMessagingServiceModule.getDataSendMessage();
      alert(res);
    } catch (error) {
      console.error(error);
    }
  }

  onTestCalendarModule = () => {
    const { CalendarModule } = NativeModules;
    // const { DEFAULT_EVENT_NAME } = CalendarModule.getConstants();
    // console.log("vc:",DEFAULT_EVENT_NAME);
    // CalendarModule.createCalendarEvent('ThangName', 'ThangLocation');
    CalendarModule.createCalendarCallBackEvent('Party',
      'My House', (eventId) => {
        console.log('YYY', eventId)
      })
  }

  render() {
    return (
      <>
        <StatusBar barStyle="dark-content" />
        <SafeAreaView style={{ flex: 1 }}>
          <Text>Gửi Notication hoặc Gửi Message và xem kết qủa</Text>
          <Text>Dùng Postmen để send</Text>

          <Text>Post: https://fcm.googleapis.com/fcm/send</Text>
          <Text>Header:</Text>
          <Text> Content-Type  || application/json</Text>
          <Text>Authorization  || key=SERVER_KEY_IN_CLOUL_MESSAGE</Text>
          <Text style={{color:'blue'}}>1.Send Notication</Text>
          <Text>Body:</Text>
          <Text>{`{
    "data": {
         "title": "Message payload title11",
         "body": "Message payload body11"
         },
         "to": YOUR_TOKEN_DEVICE,
          "notification": {"title": "Price drop", "body": "2% off all books"}

}`}</Text>
          <Text style={{color:'blue'}}>2.Send Message</Text>
          <Text>{`
          "data": {
         "title": "Message payload title11",
         "body": "Message payload body11"
         },
         "to":YOUR_TOKEN_DEVICE,`}</Text>

          <TouchableOpacity onPress={this.onTestCalendarModule}>
            <Text>click</Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={this.onClick}
            style={{ width: 100, height: 30, backgroundColor: 'green', marginTop: 20 }}>
            <Text>Test Fcm</Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={this.onClick2}
            style={{ width: 100, height: 30, backgroundColor: 'green', marginTop: 20 }}>
            <Text>Test Fcm send Param</Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={this.onClick3}
            style={{ width: 100, height: 30, backgroundColor: 'green', marginTop: 20 }}>
            <Text>get token fcm</Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={this.onClick4}
            style={{ width: 100, height: 30, backgroundColor: 'green', marginTop: 20 }}>
            <Text>get data event send message</Text>
          </TouchableOpacity>
        </SafeAreaView>
      </>
    );
  }

};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
