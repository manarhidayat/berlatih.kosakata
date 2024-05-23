import {ApiRequestModel, LoginParams} from './api_model/ApiRequest';
import StoreModel from './StoreModel';

interface AuthModel extends ApiRequestModel, StoreModel {
  loginRequest: (params: LoginParams) => void;
}

export default AuthModel;
