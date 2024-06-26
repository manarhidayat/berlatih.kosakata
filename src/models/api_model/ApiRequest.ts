export type LoginParams = {
  email: string;
  password: string;
};

export type RegisterParams = {
  email: string;
  password: string;
};

export type ApiRequestModel = {
  loading: boolean;
  error: boolean;
};
