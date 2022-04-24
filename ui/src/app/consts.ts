export class Consts{
  static readonly API_PATH: string = 'http://localhost:8000/api/';
  static readonly API_GET_AVAILABLE_DBS: string = 'presto/getAvailableDbs';
  static readonly API_GET_QUERY_RESULTS: string = 'privacy/getQueryResults';
  static readonly API_GET_CUSTOM_QUERY_RESULTS: string = 'privacy/getCustomQueryResults';
  static readonly API_GET_AVAILABLE_DB_SCHEMAS: string = 'presto/getAvailableDbSchemas';
  static readonly API_GET_AVAILABLE_SCHEMA_TABLES: string = 'presto/getAvailableSchemaTables';
  static readonly API_GET_COLUMNS_FROM_TABLE: string = 'presto/getColumnsFromTable';
  static readonly API_FILTER_OPERATIONS: string = 'presto/getFilterOperations';
  static readonly API_JOIN_OPERATIONS: string = 'presto/getJoinOperations';
  static readonly API_GET_QUERY_PRIVACY_RESULTS: string = 'privacy/getQueryResultsPrivacyChecked';
}
